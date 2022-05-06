
declare let DEBUG: boolean;
declare let DOCUMENT_INSTANCE_UUID: string;
declare let SESSION_TIMED_OUT: boolean;
declare let VERBOSE: boolean;

declare function lock(reason: number): boolean;
declare function unlock(): void;

declare function handleSessionTimeout(): void;
