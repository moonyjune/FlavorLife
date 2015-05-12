package moony.vn.flavorlife.utils;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.api.ReceivedServerError;

/**
 * Created by moony on 5/6/15.
 */
public class ValidateDataUtils {
    public static final int VALID_DATA = 1;
    public static final int SECTION_INGREDIENT_EMPTY = -1;
    public static final int SECTION_INSTRUCTION_EMPTY = -2;
    public static final int NUMBER_SECTION_NOT_THE_SAME = -3;
    public static final int SECTION_INGREDIENT_NAME_EMPTY = -4;
    public static final int SECTION_INSTRUCTION_NAME_EMPTY = -5;
    public static final int SECTION_NAME_NOT_THE_SAME = -6;
    public static final int LIST_INGREDIENTS_EMPTY = -7;
    public static final int LIST_STEPS_EMPTY = -8;
    public static final int INGREDIENT_CONTENT_EMPTY = -9;
    public static final int STEP_CONTENT_EMPTY = -10;
    public static final int RECIPE_NAME_EMPTY = -11;
    public static final int RECIPE_INTRO_EMPTY = -12;
    public static final int RECIPE_LEVEL_EMPTY = -13;
    public static final int RECIPE_COOKING_TIME_EMPTY = -14;
    public static final int RECIPE_KIND_EMPTY = -15;
    public static final int RECIPE_BOOK_EMPTY = -16;
    public static final int RECIPE_CHAPTER_EMPTY = -17;
    public static final int RECIPE_IMAGE_EMPTY = -18;
    public static final int BOOK_IMAGE_EMPTY = -19;
    public static final int BOOK_NAME_EMPTY = -20;
    public static final int BOOK_INTRO_EMPTY = -21;
    public static final int CHAPTER_NAME_EMPTY = -22;

    public static final int USERNAME_EMPTY = -19;
    public static final int EMAIL_EMPTY = -20;
    public static final int WRONG_EMAIL_PATTERN = -21;

    public static String get(Context context, int code) {
        String s = context.getString(R.string.error_unknown);
        switch (code) {
            case SECTION_INGREDIENT_EMPTY:
                return "section ingredient empty";
            case SECTION_INSTRUCTION_EMPTY:
                return "section instruction empty";
            case NUMBER_SECTION_NOT_THE_SAME:
                return "number section not the same";
            case SECTION_INGREDIENT_NAME_EMPTY:
                return "section ingredient name empty";
            case SECTION_INSTRUCTION_NAME_EMPTY:
                return "section instruction name empty";
            case SECTION_NAME_NOT_THE_SAME:
                return "section name not the same";
            case LIST_INGREDIENTS_EMPTY:
                return "list ingredients empty";
            case LIST_STEPS_EMPTY:
                return "list steps empty";
            case INGREDIENT_CONTENT_EMPTY:
                return "ingridient content empty";
            case STEP_CONTENT_EMPTY:
                return "step content empty";
            case RECIPE_NAME_EMPTY:
                return "recipe name empty";
            case RECIPE_INTRO_EMPTY:
                return "recipe introduction empty";
            case RECIPE_LEVEL_EMPTY:
                return "recipe level empty";
            case RECIPE_KIND_EMPTY:
                return "recipe kind empty";
            case RECIPE_BOOK_EMPTY:
                return "recipe book empty";
            case RECIPE_CHAPTER_EMPTY:
                return "recipe chapter empty";
            case RECIPE_COOKING_TIME_EMPTY:
                return "recipe cooking time empty";
            case RECIPE_IMAGE_EMPTY:
                return "recipe image empty";
            case BOOK_IMAGE_EMPTY:
                return "book image empty";
            case BOOK_INTRO_EMPTY:
                return "book intro empty";
            case BOOK_NAME_EMPTY:
                return "book name empty";
            case CHAPTER_NAME_EMPTY:
                return "book name empty";
        }
        return s;
    }
}
