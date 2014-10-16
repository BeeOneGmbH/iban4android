/*
 * Copyright 2014 Michael Greifeneder
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
package com.github.mikegr.iban4android;

import android.content.Context;

import org.iban4j.UnsupportedCountryException;

import static org.iban4j.UnsupportedCountryException.Constraint.*;
import com.github.mikegr.iban4android.R;

public class UnsupportedCountryExceptionFormatter extends AndroidExceptionFormatter {


    public UnsupportedCountryExceptionFormatter(Context ctx) {
        super(ctx);
    }

    public String getMessage(UnsupportedCountryException ex) {
        Object[] values = ex.getValues();
        switch(ex.getConstraint()) {
            case upper_case: return ctx.getString(R.string.error_msg_iban_country_code_upper_case);
            case non_existing: return ctx.getString(R.string.error_msg_non_existing_country_code);
            case not_supported: return ctx.getString(R.string.error_msg_iban_country_code_not_supported, values);
            case is_null: return ctx.getString(R.string.error_msg_iban_country_code_required);
        }
        return ex.getMessage();
    }

}
