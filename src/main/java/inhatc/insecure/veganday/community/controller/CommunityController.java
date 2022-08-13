package inhatc.insecure.veganday.community.controller;

import inhatc.insecure.veganday.common.model.ResponseFmt;
import inhatc.insecure.veganday.common.model.ResponseMessage;
import inhatc.insecure.veganday.common.model.StatusCode;
import inhatc.insecure.veganday.community.model.Community;
import inhatc.insecure.veganday.community.repository.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    CommunityRepository communityRepository;

    @GetMapping("")
    public ResponseEntity findAll(){
//        return communityRepository.findAll(Sort.by(Sort.Direction.DESC, "writeDt"));
        List<Community> list = communityRepository.findAll(Sort.by(Sort.Direction.DESC, "writeDt"));
        return new ResponseEntity(ResponseFmt.res(StatusCode.OK, ResponseMessage.READ_BOARDS, list), HttpStatus.OK);
    }
}
