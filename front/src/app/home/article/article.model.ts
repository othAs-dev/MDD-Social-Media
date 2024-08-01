export interface Article {
  id: string;
  title: string;
  description: string;
  username: string;
  createdAt: string;
  topicTitle: string;
}

export type Articles = Article[];
