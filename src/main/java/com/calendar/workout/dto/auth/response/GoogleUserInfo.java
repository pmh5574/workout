package com.calendar.workout.dto.auth.response;

import com.calendar.workout.dto.auth.OauthUserInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoogleUserInfo implements OauthUserInfo {

    private String id;

    private String email;

    private String name;
}
