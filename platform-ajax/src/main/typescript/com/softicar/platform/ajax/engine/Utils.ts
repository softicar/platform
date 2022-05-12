/**
 * Pushes the given URL into the history of the browser.
 */
function pushBrowserHistoryState(page: string, url: string) {
    history.pushState({page: page}, "", url);
}
