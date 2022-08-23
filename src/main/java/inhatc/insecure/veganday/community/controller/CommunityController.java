package inhatc.insecure.veganday.community.controller;

import inhatc.insecure.veganday.common.model.*;
import inhatc.insecure.veganday.common.service.FileService;
import inhatc.insecure.veganday.community.model.*;
import inhatc.insecure.veganday.community.repository.AttachfileRepository;
import inhatc.insecure.veganday.community.repository.CommentRepository;
import inhatc.insecure.veganday.community.repository.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    CommunityRepository communityRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    FileService fileService;

    @Autowired
    AttachfileRepository attachfileRepository;

    @GetMapping("")
    public ResponseEntity list(@RequestParam(required = false, defaultValue = "") String searchText){
        List<Board> list = communityRepository.findList(searchText, Sort.by(Sort.Direction.DESC, "writeDt"));

        List<BoardListDTO> boardList = new ArrayList<>();
        // 날짜 형식 변경
        for (Board item: list) {

            DateTimeFormatter MD = DateTimeFormatter.ofPattern("MM/dd");
            DateTimeFormatter HM = DateTimeFormatter.ofPattern("HH:mm");

            LocalDate now = LocalDate.now();
            LocalDateTime nowDt = now.atTime(0,0,0);

            String writeDt = "";
            if (item.getWriteDt().isBefore(nowDt)) {
                //월일 형태로
                writeDt = item.getWriteDt().format(MD);
            } else {
                //시분 형태로
                writeDt = item.getWriteDt().format(HM);
            }

            String filepath = item.getAttachfile() == null ? "" : item.getAttachfile().getFilepath();

            BoardListDTO board = BoardListDTO.res(item.getBid(), item.getTitle(), item.getUserName(), item.getHit(), item.getComment(), writeDt, filepath);
            boardList.add(board);
        }

        return new ResponseEntity(ResponseFmt.res(StatusCode.OK, ResponseMessage.READ_BOARDS, boardList), HttpStatus.OK);
    }

    @GetMapping("/{bid}/board")
    public ResponseEntity detailByBoard(@PathVariable(name = "bid") Long bid){
        int result = 0;
        result = communityRepository.updateHit(bid);

        if(result > 0) {
            List<Board> board = communityRepository.findDetail(bid);
            return new ResponseEntity(ResponseFmt.res(StatusCode.OK, ResponseMessage.READ_BOARD_DETAIL, board), HttpStatus.OK);
        } else {
            return new ResponseEntity(ResponseFmt.res(StatusCode.BAD_REQUEST, ResponseMessage.NOT_FOUND_BOARD_DETAIL), HttpStatus.OK);
        }
    }

    @GetMapping("/{bid}/comment")
    public ResponseEntity detailByComment(@PathVariable(name = "bid") Long bid){
        List<Comment> comments = commentRepository.findComments(bid);

        return new ResponseEntity(ResponseFmt.res(StatusCode.OK, ResponseMessage.READ_BOARD_DETAIL, comments), HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity upload(MultipartHttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file){

        Board board = Board.builder()
                .bid(System.currentTimeMillis())
                .title(request.getParameter("title"))
                .cn(request.getParameter("cn"))
                .userName(request.getParameter("userName"))
                .build();
        communityRepository.save(board);

        if(file != null) {
            FileDTO filedto = fileService.uploadFile(file);

            if (filedto.getCode() == -2) {
                return new ResponseEntity(ResponseFmt.res(StatusCode.BAD_REQUEST, ResponseMessage.IMAGE_UPLOAD_ERROR), HttpStatus.OK);
            }
            if (filedto.getCode() == -1) {
                return new ResponseEntity(ResponseFmt.res(StatusCode.BAD_REQUEST, ResponseMessage.CANT_NOT_OTHER_FILES), HttpStatus.OK);
            }

            Attachfile attachfile = Attachfile.builder()
                    .fid(System.currentTimeMillis())
                    .filename(filedto.getFilename())
                    .filepath(filedto.getFilepath())
                    .board(board)
                    .build();

            attachfileRepository.save(attachfile);
        }

        return new ResponseEntity(ResponseFmt.res(StatusCode.OK, ResponseMessage.SAVE_NEW_BOARD), HttpStatus.OK);
    }

    @PostMapping("/comment")
    public ResponseEntity comment(@RequestBody Comment comment){

        long millis = System.currentTimeMillis();
        comment.setCid(millis);

        Object obj = commentRepository.save(comment);

        return new ResponseEntity(ResponseFmt.res(StatusCode.OK, ResponseMessage.SAVE_NEW_COMMENT, obj), HttpStatus.OK);
    }
}
