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

import org.iban4j.CountryCode;
import org.iban4j.IbanFormatException;
import org.iban4j.IbanUtil;
import org.iban4j.UnsupportedCountryException;
import org.iban4j.bban.BbanStructure;
import org.iban4j.bban.BbanStructureEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.iban4j.IbanFormatException.IbanFormatViolation.BBAN_LENGTH;

/** IbanChecker accepts incomplete IBANs
 *
 */
public class IbanChecker {


    private static final int COUNTRY_CODE_INDEX = 0;
    private static final int COUNTRY_CODE_LENGTH = 2;
    private static final int CHECK_DIGIT_INDEX = COUNTRY_CODE_LENGTH;
    private static final int CHECK_DIGIT_LENGTH = 2;
    private static final int BBAN_INDEX = CHECK_DIGIT_INDEX + CHECK_DIGIT_LENGTH;

    private List<String> validCountries;

    public IbanChecker() {
    }

    /**
     * Only countries in validCountries are allowed
     * @param validCountries
     */
    public IbanChecker(List<String> validCountries) {
        this.validCountries = validCountries;
    }

    /**
     * Only countries in validCountries are allowed
     * @param ctx
     * @param valid_countries resource id of string array
     */
    public IbanChecker(Context ctx, int valid_countries) {
        this.validCountries = Arrays.asList(ctx.getResources().getStringArray(valid_countries));
    }

    /**
     * Throws Exception on error case.
     * @param iban
     * @return true if exact required size, false otherwise
     */
    public boolean check(String iban) {
        if (iban.length() >= 1) {
            if (!Character.isLetter(iban.charAt(0))) {
                throw new IbanFormatException(IbanFormatException.IbanFormatViolation.COUNTRY_CODE_UPPER_CASE_LETTERS, "Iban country code must contain upper case letters.");
            }
            if (iban.length() >= 2) {
                if (!Character.isLetter(iban.charAt(1))) {
                    throw new IbanFormatException(IbanFormatException.IbanFormatViolation.COUNTRY_CODE_UPPER_CASE_LETTERS, "Iban country code must contain upper case letters.");
                }
                checkValidCountryCode(iban);
                if (iban.length() >= 3) {
                    if (!Character.isDigit(iban.charAt(2))) {
                        throw new IbanFormatException(IbanFormatException.IbanFormatViolation.CHECK_DIGIT_ONLY_DIGITS, "Iban's check digit should contain only digits.");
                    }
                    if (iban.length() >= 4) {
                        if (!Character.isDigit(iban.charAt(3))) {
                            throw new IbanFormatException(IbanFormatException.IbanFormatViolation.CHECK_DIGIT_ONLY_DIGITS, "Iban's check digit should contain only digits.");
                        }
                    }
                    if (iban.length() > 4) {
                        int maxLength = getIbanLength(iban);
                        if (iban.length() > maxLength) {
                            int bbanLength = iban.length() -4;
                            int expectedBbanLength = maxLength -4;
                            throw new IbanFormatException(BBAN_LENGTH,
                                    iban.length() -4, maxLength - 4,
                                    "Length is " + bbanLength +
                                            ", expected BBAN length is: " + expectedBbanLength);

                        }
                        validateIncompleteBban(iban);
                        if (iban.length() == maxLength) {
                            IbanUtil.validate(iban);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Advanced check for valid BBanStructure to do further checks with BBAN.
     * @param iban
     * @return
     */
    private void checkValidCountryCode(String iban) {
        String cc = IbanUtil.getCountryCode(iban);
        if (validCountries != null && ! validCountries.contains(cc)) {
            throw new UnsupportedCountryException(cc, "Country code: " + cc + " is not supported.");
        }
        CountryCode countryCode = CountryCode.getByCode(cc);
        if (! IbanUtil.isSupportedCountry(countryCode)) {
            throw new UnsupportedCountryException(cc, "Country code: " + cc + " is not supported.");
        };

    }

    private static void validateIncompleteBban(final String incompleteIban)  {
        CountryCode cc = CountryCode.getByCode(IbanUtil.getCountryCode(incompleteIban));
        BbanStructure bbanStructure = BbanStructure.forCountry(cc);
        String bban = IbanUtil.getBban(incompleteIban);
        List<BbanStructureEntry.EntryCharacterType> list = generateCharacterTypeList(bbanStructure);

        if (bban.length() > list.size()) throw new IbanFormatException(IbanFormatException.IbanFormatViolation.BBAN_LENGTH, incompleteIban.length(), list.size() + 4, incompleteIban);

        for(int i = 0; i < bban.length(); i++) {
            BbanStructureEntry.EntryCharacterType type = list.get(i);
            if (! validateEntryCharacterType(type, bban.charAt(i))) {
                switch(type) {
                    case a: throw new IbanFormatException(IbanFormatException.IbanFormatViolation.BBAN_ONLY_UPPER_CASE_LETTERS, ""+ (BBAN_INDEX +i));
                    case c: throw new IbanFormatException(IbanFormatException.IbanFormatViolation.BBAN_ONLY_DIGITS_OR_LETTERS, "" + (BBAN_INDEX +i));
                    case n: throw new IbanFormatException(IbanFormatException.IbanFormatViolation.BBAN_ONLY_DIGITS, "" + (BBAN_INDEX +i));
                }
            }
        }
    }

    public static List<BbanStructureEntry.EntryCharacterType> generateCharacterTypeList(BbanStructure structure) {
        ArrayList<BbanStructureEntry.EntryCharacterType> list = new ArrayList<BbanStructureEntry.EntryCharacterType>();
        for(BbanStructureEntry entry:structure.getEntries()) {
            for(int i = 0; i < entry.getLength(); i++) {
                list.add(entry.getCharacterType());
            }
        }
        return list;
    }



    private static boolean validateEntryCharacterType(final BbanStructureEntry.EntryCharacterType type, char ch) {
        switch (type) {
            case a: return Character.isUpperCase(ch);
            case c: return Character.isLetterOrDigit(ch);
            case n: return Character.isDigit(ch);
        }
        return false;
    }


    public static int getIbanLength(String iban) {
        CountryCode cc = CountryCode.getByCode(IbanUtil.getCountryCode(iban));
        if (! IbanUtil.isSupportedCountry(cc)) {
            return 2;
        }
        return IbanUtil.getIbanLength(cc);
    }

}

