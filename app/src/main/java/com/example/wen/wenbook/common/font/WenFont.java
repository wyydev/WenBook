package com.example.wen.wenbook.common.font;

import android.content.Context;
import android.graphics.Typeface;

import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.iconics.typeface.ITypeface;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by wen on 2017/3/18.
 */

public class WenFont implements ITypeface {

    private static final String TTF_FILE = "iconfont.ttf";
    private static Typeface typeface = null;
    private static HashMap<String, Character> mChars;

    @Override
    public IIcon getIcon(String key) {
        return WenFont.Icon.valueOf(key);
    }

    @Override
    public HashMap<String, Character> getCharacters() {
        if (mChars == null) {
            HashMap<String, Character> aChars = new HashMap<String, Character>();
            for (WenFont.Icon v : WenFont.Icon.values()) {
                aChars.put(v.name(), v.character);
            }
            mChars = aChars;
        }
        return mChars;
    }

    @Override
    public String getMappingPrefix() {
        return "wen";
    }

    @Override
    public String getFontName() {
        return "wen";
    }

    @Override
    public String getVersion() {
        return "1.0.1";
    }

    @Override
    public int getIconCount() {
        return mChars.size();
    }

    @Override
    public Collection<String> getIcons() {
        Collection<String> icons = new LinkedList<String>();
        for (WenFont.Icon value : WenFont.Icon.values()) {
            icons.add(value.name());
        }
        return icons;
    }

    @Override
    public String getAuthor() {
        return "Wen";
    }

    @Override
    public String getUrl() {
        return "https://github.com/wyydev";
    }

    @Override
    public String getDescription() {
        return "The premium icon font for Ionic Framework.";
    }

    @Override
    public String getLicense() {
        return "MIT Licensed";
    }

    @Override
    public String getLicenseUrl() {
        return "https://github.com/wyydev";
    }

    @Override
    public Typeface getTypeface(Context context) {
        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(),TTF_FILE);
            } catch (Exception e) {
                return null;
            }
        }
        return typeface;
    }

    public enum Icon implements IIcon {

        wen_share('\ue739'),
        wen_love('\ue643'),
        wen_love_fill('\ue684'),
        wen_link('\ue646'),
        wen_scanner('\ue649'),
        wen_search('\ue651'),
        wen_save_as('\ue666'),
        wen_user('\ue657'),
        wen_book('\ue767'),
        wen_shutdown('\ue621'),
        wen_add('\ue622'),
        wen_smile('\ue641'),
        wen_write('\ue662');

        char character;

        Icon(char character) {
            this.character = character;
        }

        public String getFormattedName() {
            return "{" + name() + "}";
        }

        public char getCharacter() {
            return character;
        }

        public String getName() {
            return name();
        }

        // remember the typeface so we can use it later
        private static ITypeface typeface;

        public ITypeface getTypeface() {
            if (typeface == null) {
                typeface = new WenFont();
            }
            return typeface;
        }
    }

}
