
declare let SESSION_TIMED_OUT: boolean;

declare function SR_sendAjaxRequest(message: any, form: any): void;

declare function lock(reason: number): boolean;
declare function unlock(): void;
