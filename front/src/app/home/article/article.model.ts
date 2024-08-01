import {Comments} from "@app/home/article/detail/comment/comment.model";

export interface Article {
  id: string;
  title: string;
  description: string;
  username: string;
  createdAt: string;
  topicTitle: string;
  comments: Comments;
}

export type Articles = Article[];
