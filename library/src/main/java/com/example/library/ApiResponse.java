/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.library;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import retrofit2.Response;

/**
 * Common class used by API responses.
 *
 * @param <T>
 */
public class ApiResponse<T> {

    @SerializedName("code")
    public int code;

    @SerializedName("data")
    public T data;

    @SerializedName("message")
    public String message;

    public ApiResponse(Response<T> response) {
        code = response.code();
        if (response.isSuccessful()) {
            data = response.body();
            message = null;
        } else {
            String msg = null;
            if (response.errorBody() != null) {
                try {
                    msg = response.errorBody().string();
                } catch (Exception e) {
                    Log.e("ApiResponse", "error while parsing response");
                }
            }
            if (msg == null || msg.trim().length() == 0) {
                msg = response.message();
            }
            message = msg;
            data = null;
        }
    }

    public ApiResponse(Throwable error) {
        code = 500;
        data = null;
        message = error.getMessage();
    }

    public boolean isSuccessful() {
        return code == 200;
    }

}
