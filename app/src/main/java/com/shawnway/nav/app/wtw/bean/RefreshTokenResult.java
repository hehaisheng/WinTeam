package com.shawnway.nav.app.wtw.bean;

/**
 * Created by Cicinnus on 2016/12/6.
 */

public class RefreshTokenResult {

    /**
     * access_token : e5e5811b-70f2-4b42-bc77-0fe68e359a34
     * token_type : bearer
     * refresh_token : d1420265-33c4-4c05-b64d-dbbee0fdd103
     * expires_in : 3599
     * scope : read write trust
     */

    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
