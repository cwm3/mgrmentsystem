package org.cwm3.mgrsystem.model;

import java.io.Serializable;

/**

 * @作者 cwm3

 * @时间 2019-09-27 7:07
 */
public class Meta implements Serializable {
    private Boolean keepAlive;

    private Boolean requireAuth;

    public Boolean getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public Boolean getRequireAuth() {
        return requireAuth;
    }

    public void setRequireAuth(Boolean requireAuth) {
        this.requireAuth = requireAuth;
    }
}
