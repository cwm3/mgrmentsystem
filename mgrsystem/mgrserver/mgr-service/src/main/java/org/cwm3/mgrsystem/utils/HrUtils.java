package org.cwm3.mgrsystem.utils;

import org.cwm3.mgrsystem.model.Hr;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @作者 cwm3
 */
public class HrUtils {
    public static Hr getCurrentHr() {
        return ((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
