
function pushBrowserHistoryState(page, url) {

    history.pushState({page: page}, "", url);
}