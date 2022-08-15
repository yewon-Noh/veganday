package inhatc.insecure.veganday.community.controller;

import inhatc.insecure.veganday.common.model.Pagenation;
import inhatc.insecure.veganday.common.model.ResponseFmt;
import inhatc.insecure.veganday.common.model.ResponseMessage;
import inhatc.insecure.veganday.common.model.StatusCode;
import inhatc.insecure.veganday.community.model.Board;
import inhatc.insecure.veganday.community.model.BoardListDTO;
import inhatc.insecure.veganday.community.repository.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

            BoardListDTO board = BoardListDTO.res(item.getBid(), item.getTitle(), item.getUserId(), item.getHit(), item.getCommnet(), writeDt);
            boardList.add(board);
        }

        return new ResponseEntity(ResponseFmt.res(StatusCode.OK, ResponseMessage.READ_BOARDS, boardList), HttpStatus.OK);
    }

    @GetMapping("{bid}")
    public ResponseEntity detail(@RequestParam int bid){


        return new ResponseEntity(ResponseFmt.res(StatusCode.OK, ResponseMessage.READ_BOARDS, ""), HttpStatus.OK);
    }


    @GetMapping("/list")
    public ResponseEntity listByPagenation(@PageableDefault(size = 10) Pageable pageable,
                               @RequestParam(required = false, defaultValue = "") String searchText) {

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "writeDt");

        Page<Board> list = communityRepository.findListNonCnWithPage(searchText, pageRequest);

        Pagenation pg = Pagenation.res(list.getTotalPages(), list.getContent());

        return new ResponseEntity(ResponseFmt.res(StatusCode.OK, ResponseMessage.READ_BOARDS, pg), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity upload(@RequestBody Board board){

        long millis = System.currentTimeMillis();
        board.setBid(millis);

        Object obj = communityRepository.save(board);

        return new ResponseEntity(ResponseFmt.res(StatusCode.OK, ResponseMessage.SAVE_NEW_BOARD, obj), HttpStatus.OK);
    }
}
