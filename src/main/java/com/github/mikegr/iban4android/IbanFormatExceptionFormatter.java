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

import android.annotation.SuppressLint;
import android.content.Context;

import org.iban4j.IbanFormatException;
import com.github.mikegr.iban4android.R;

import static org.iban4j.IbanFormatException.*;
import static org.iban4j.IbanFormatException.IbanFormatViolation.BBAN_LENGTH;
import static org.iban4j.IbanFormatException.IbanFormatViolation.BBAN_ONLY_DIGITS;
import static org.iban4j.IbanFormatException.IbanFormatViolation.BBAN_ONLY_DIGITS_OR_LETTERS;
import static org.iban4j.IbanFormatException.IbanFormatViolation.BBAN_ONLY_UPPER_CASE_LETTERS;
import static org.iban4j.IbanFormatException.IbanFormatViolation.CHECK_DIGIT_ONLY_DIGITS;
import static org.iban4j.IbanFormatException.IbanFormatViolation.CHECK_DIGIT_TWO_DIGITS;
import static org.iban4j.IbanFormatException.IbanFormatViolation.COUNTRY_CODE_EXISTS;
import static org.iban4j.IbanFormatException.IbanFormatViolation.COUNTRY_CODE_TWO_LETTERS;
import static org.iban4j.IbanFormatException.IbanFormatViolation.COUNTRY_CODE_UPPER_CASE_LETTERS;
import static org.iban4j.IbanFormatException.IbanFormatViolation.IBAN_NOT_NULL;

public class IbanFormatExceptionFormatter extends AndroidExceptionFormatter {


    public IbanFormatExceptionFormatter(Context ctx) {
        super(ctx);
    }

    @SuppressLint("StringFormatMatches")
    public String getMessage(IbanFormatException ex) {
        switch(ex.getFormatViolation()) {
            case BBAN_LENGTH: return ctx.getString(R.string.error_msg_invalid_length, (Integer) ex.getActual()+4, (Integer)ex.getExpected()+4);
            case IBAN_NOT_NULL: return ctx.getString(R.string.error_msg_null_iban);
            case CHECK_DIGIT_ONLY_DIGITS: return ctx.getString(R.string.error_msg_iban_checksum_only_numeric);
            case CHECK_DIGIT_TWO_DIGITS: return ctx.getString(R.string.error_msg_iban_checksum_only_numeric);
            case BBAN_ONLY_UPPER_CASE_LETTERS: return ctx.getString(R.string.error_msg_bban_pos_uppercase_only, ""); //TODO missing value
            case BBAN_ONLY_DIGITS: return ctx.getString(R.string.error_msg_bban_pos_numeric_only, ""); //TODO missing value
            case BBAN_ONLY_DIGITS_OR_LETTERS: return ctx.getString(R.string.error_msg_bban_pos_alphanumeric_only, "");  //TODO missing value

            case COUNTRY_CODE_TWO_LETTERS:return ctx.getString(R.string.error_msg_iban_country_code_upper_case);
            case COUNTRY_CODE_UPPER_CASE_LETTERS: return ctx.getString(R.string.error_msg_iban_country_code_upper_case);
            case COUNTRY_CODE_EXISTS: return ctx.getString(R.string.error_msg_non_existing_country_code);
        }
        return ex.getMessage();
    }
}
