export interface StatusType {
    type: "warning" | "success" | "error" | "default" | "processing" | undefined;
    content: string;
}