export interface ProfileResponseModel {
    consecutiveLearningDayLimit: number;
    quarterlyLearningDayLimit: number;
    manager: {
        id: number;
        email: string;
        name: string;
        surname: string;
    };
}