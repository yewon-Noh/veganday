package inhatc.insecure.veganday.community.controller;

import inhatc.insecure.veganday.community.model.Community;
import inhatc.insecure.veganday.community.repository.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    public List<Community> findAll(){
        return communityRepository.findAll(Sort.by(Sort.Direction.DESC, "writeDt"));
    }
}
