package org.cwm3.system.utils;

import org.cwm3.system.model.Hr;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @作者 cwm3
 */
public class HrUtils {
    public static Hr getCurrentHr() {
        return ((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
