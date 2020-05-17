export interface LearningDayResponse {
    id: number;
    name: string;
    comment: string;
    dateTimeAt: string;
    assignee: {
        id: number;
    };
}