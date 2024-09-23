package hotelbooking.infra;

import hotelbooking.config.kafka.KafkaProcessor;
import hotelbooking.domain.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class MypageViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private MypageRepository mypageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenBooked_then_CREATE_1(@Payload Booked booked) {
        try {
            if (!booked.validate()) return;

            // view 객체 생성
            Mypage mypage = new Mypage();
            // view 객체에 이벤트의 Value 를 set 함
            mypage.setUserId(booked.getUserId());
            mypage.setBookId(booked.getBookId());
            mypage.setHotelId(booked.getHotelId());
            mypage.setHotelName(booked.getHotelName());
            mypage.setBookDt(booked.getBookDt());
            mypage.setBookStatus(booked.getBookStatus());
            // view 레파지 토리에 save
            mypageRepository.save(mypage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//
    @StreamListener(KafkaProcessor.INPUT)
    public void whenPointdecreased_then_UPDATE_1( 
        @Payload Pointdecreased pointdecreased
    ) {
        try {
            if (!pointdecreased.validate()) return;
            // view 객체 조회

            List<Mypage> mypageList = mypageRepository.findByUserId(
                pointdecreased.getUserId()
            );
            for (Mypage mypage : mypageList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                mypage.setUserpoint(pointdecreased.getUserpoint());
                // view 레파지 토리에 save
                mypageRepository.save(mypage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPointincreased_then_UPDATE_2(
        @Payload Pointincreased pointincreased
    ) {
        try {
            if (!pointincreased.validate()) return;
            // view 객체 조회

            List<Mypage> mypageList = mypageRepository.findByUserId(
                pointincreased.getUserId()
            );
            for (Mypage mypage : mypageList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                mypage.setUserpoint(pointincreased.getUserpoint());
                // view 레파지 토리에 save
                mypageRepository.save(mypage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
