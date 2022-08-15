package inhatc.insecure.veganday.community.controller;

import inhatc.insecure.veganday.common.model.Pagenation;
import inhatc.insecure.veganday.common.model.ResponseFmt;
import inhatc.insecure.veganday.common.model.ResponseMessage;
import inhatc.insecure.veganday.common.model.StatusCode;
import inhatc.insecure.veganday.community.model.Board;
import inhatc.insecure.veganday.community.model.BoardDTO;
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

import java.util.List;

@RestController
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    CommunityRepository communityRepository;

    @GetMapping("")
    public ResponseEntity list(@RequestParam(required = false, defaultValue = "") String searchText){
        List<BoardDTO> list = communityRepository.findList(searchText, Sort.by(Sort.Direction.DESC, "writeDt"));
        return new ResponseEntity(ResponseFmt.res(StatusCode.OK, ResponseMessage.READ_BOARDS, list), HttpStatus.OK);
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

        Object obj = communityRepository.save(board);

        return new ResponseEntity(ResponseFmt.res(StatusCode.OK, ResponseMessage.SAVE_NEW_BOARD, obj), HttpStatus.OK);
    }
}
