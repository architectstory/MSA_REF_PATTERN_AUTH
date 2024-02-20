package com.samsungsds.msa.auth.adapter;

public interface AuthenticationAdapter<Request, Response> {
    Response initiateAuthentication(Request request) throws Exception;
    Response authorization(Request request) throws Exception;
}
