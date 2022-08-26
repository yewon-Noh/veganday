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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

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

    @Value("${telegram.token}")
    private String telegramToken;

    @Value("${telegram.id}")
    private String telegramId;

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
        String Token = telegramToken;
        String chat_id = telegramId;
        
        BufferedReader in = null;
        
        try {
            System.out.println("telegram send");
            URL obj = new URL("https://api.telegram.org/bot" + Token + "/sendmessage?chat_id=" + chat_id + "&text=" + request.getParameter("userName")+"님께서 게시물을 등록하였습니다.%0A제목: "+request.getParameter("title")+"%0A내용: "+request.getParameter("cn")); // 호출할 url
            
            HttpURLConnection con = (HttpURLConnection)obj.openConnection();
            con.setRequestMethod("GET");
            in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));	 
            
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(in != null) try { in.close(); } catch(Exception e) { e.printStackTrace(); }
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
