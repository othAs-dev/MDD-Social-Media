import {Id} from "@app/shared/models/id.model";

export interface Comment {
  id: Id;
  content: string;
  username: string;
  createdAt: string;
  articleId: Id;
}

export type Comments = Comment[];
