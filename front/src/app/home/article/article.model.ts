export interface Article {
  id: string;
  title: string;
  content: string;
  topicId: string;
}

export type Articles = Article[];
