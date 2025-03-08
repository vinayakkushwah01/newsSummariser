package com.newsSummeriser.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsSummeriser.model.NewsCard;
import com.newsSummeriser.repository.NewsCardRepo;
@Service
public class HomePageService {
   @Autowired
    NewsCardRepo newsCardRepo;

    // public List<NewsCard>  getTopNews(){
    //   // return  newsCardRepo.findByTopSectionOrderByDateDesc("सबसे ज़्यादा पढ़े गए");

    // }
}
