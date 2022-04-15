package com.ejder.socialapp.core.business;

import com.ejder.socialapp.core.utils.results.Result;

public class BusinesRules {
    public static Result run(Result ...logics){
        for(Result logic:logics){
            if (!logic.isSuccess()){
                return logic;
            }
        }
        return null;
    }
}
