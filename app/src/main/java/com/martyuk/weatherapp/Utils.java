package com.martyuk.weatherapp;

public class Utils {
    public static int chooseImageByWeatherCode(int code) {
        switch (code) {
            case 395:
            case 377:
            case 350:
            case 335:
            case 230:
                return R.drawable.iconfinder_snowy_5729379;
            case 392:
            case 374:
            case 371:
            case 326:
                return R.drawable.iconfinder_snowing_5729381;
            case 389:
                return R.drawable.iconfinder_lightning_5729386;
            case 386:
            case 200:
                return R.drawable.iconfinder_lightning_cloudy_5729387;
            case 368:
            case 365:
            case 362:
            case 338:
            case 332:
            case 329:
            case 323:
            case 320:
            case 317:
            case 182:
            case 179:
                return R.drawable.iconfinder_snow_clody_5729380;
            case 359:
            case 356:
            case 314:
            case 311:
            case 308:
            case 305:
            case 302:
            case 299:
            case 296:
            case 293:
            case 176:
                return R.drawable.iconfinder_raining_5729383;
            case 353:
            case 284:
            case 281:
            case 266:
            case 263:
            case 185:
                return R.drawable.iconfinder_drip_5729390;
            case 260:
            case 248:
            case 143:
                return R.drawable.iconfinder_foggy_5729388;
            case 227:
                return R.drawable.iconfinder_windy_5729377;
            case 122:
            case 119:
                return R.drawable.iconfinder_cloudy_5729391;
            case 116:
                return R.drawable.iconfinder_cloudy_sunny_5729392;
            case 113:
                return R.drawable.iconfinder_sunny_5729378;

        }
        return R.drawable.iconfinder_sunny_5729378;
    }
}
