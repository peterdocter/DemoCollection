package com.github.airk.offloader;

/**
 * Match Factory for match file name
 *
 * example:
 *
 *          in html                                         local
 *              ^                                             ^
 *              |                                             |
 *                                   ______________
 *     main.css?v=18384717273  ---> | MatchFactory | ---> main.css
 *                                   --------------
 *     then if your local offline dir really has the main.css file, it hit!
 */
public interface MatchFactory {
    String match(String src);
}
