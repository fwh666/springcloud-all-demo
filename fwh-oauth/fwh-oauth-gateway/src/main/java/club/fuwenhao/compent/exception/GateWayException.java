package club.fuwenhao.compent.exception;

import club.fuwenhao.vo.SystemErrorType;
import lombok.Data;

/**
 * 网关异常
 * Created by smlz on 2019/12/26.
 */
@Data
public class GateWayException extends RuntimeException {

    private String code;

    private String msg;

    public GateWayException(SystemErrorType systemErrorType) {
        this.code = systemErrorType.getStatus();
        this.msg = systemErrorType.getMsg();
    }

}
