package com.a603.tonemate.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PitchResult {
    private int pitch;
    private boolean isSuccess;
}
