package com.sparta.viewfinder.entity;

import com.sparta.viewfinder.dto.ProfileUpdateRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "profile")
public class Profile extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String headline;

    private String phoneNumber;

    private String sns;



    public Profile(User user){
        this.user = user;
    }

    public void update(ProfileUpdateRequestDto profileUpdateRequestDto){
        this.headline = profileUpdateRequestDto.getHeadline();
        this.phoneNumber = profileUpdateRequestDto.getPhoneNumber();
        this.sns = profileUpdateRequestDto.getSns();
    }
}
