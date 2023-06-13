package com.example.project;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    //entity가 생성될 때 시간이 자동으로 저장
    //entity 생성 일자
    @CreatedDate
    @Column(updatable = false) // 명시적으로 업데이트 되지 말아야할 정보에 대해서 선언
    private LocalDateTime createdDate;

    //entity 변경 날짜
    @LastModifiedDate
    private LocalDateTime modifiedDate;


}
