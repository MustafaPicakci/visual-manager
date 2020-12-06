package com.application.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.backend.repository.TagRepository;
import com.application.backend.repository.UserRepository;

@Service
public class TagServiceImpl implements TagService {

  @Autowired TagRepository tagRepository;
  @Autowired UserRepository userRepository;

  @Override
  public void deleteTag(long tagId) {
    tagRepository.deleteById(tagId);
  }
  /*
  @Override
  public List<Tags> findTagsForUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    User user = userRepository.findByUsername(username);

    return tagRepository.findAllByUser(user);
  }*/
  /*
    @Override
    public void deleteByUser(User user) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String username = authentication.getName();
      User user = userRepository.findByUsername(username);

      tagRepository.deleteByUser(user);
    }
  */
}
