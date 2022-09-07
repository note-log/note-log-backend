package com.snowwarrior.notelog.exception;

import com.snowwarrior.notelog.constant.ErrorConstants;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class InvalidOperationException extends AbstractThrowableProblem {
    public InvalidOperationException(String message) {
        super(ErrorConstants.DEFAULT_TYPE, message, Status.CONFLICT);
    }
}
