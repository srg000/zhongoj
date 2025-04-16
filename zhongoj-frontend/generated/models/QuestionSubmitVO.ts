/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { JudgeInfo } from './JudgeInfo';
import type { UserVO } from './UserVO';
export type QuestionSubmitVO = {
    code?: string;
    id?: number;
    judgeInfo?: JudgeInfo;
    language?: string;
    questionId?: number;
    status?: number;
    title?: string;
    user?: UserVO;
    userId?: number;
};

