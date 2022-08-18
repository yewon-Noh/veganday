package inhatc.insecure.veganday.main.controller;

import inhatc.insecure.veganday.common.model.Pagenation;
import inhatc.insecure.veganday.common.model.ResponseFmt;
import inhatc.insecure.veganday.common.model.ResponseMessage;
import inhatc.insecure.veganday.common.model.StatusCode;
import inhatc.insecure.veganday.community.model.Board;
import inhatc.insecure.veganday.community.repository.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

    @Autowired
    CommunityRepository communityRepository;

    @GetMapping("")
    public String main(){
        return "hello word!";
    }

    @GetMapping("/best")
    public ResponseEntity best(){

        PageRequest pageRequest = PageRequest.of(0, 5, Sort.Direction.DESC, "hit");
        Page<Board> list = communityRepository.findListB5(pageRequest);
        List<Board> best = list.getContent();

        return new ResponseEntity(ResponseFmt.res(StatusCode.OK, ResponseMessage.READ_BOARDS, list.getContent()), HttpStatus.OK);
    }
}
