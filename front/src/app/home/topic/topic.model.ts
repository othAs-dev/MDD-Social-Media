import {Id} from "@app/shared/models/id.model";
import {Articles} from "@app/home/article/article.model";

export interface Topic {
  id: Id;
  title: string;
  description: string;
  articles: Articles;
  subscribed: boolean;
}

export type Topics = Topic[];
