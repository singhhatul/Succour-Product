import { ActivityObject } from './activity-object';

export interface Activity {
  id: string;
  timestamp: number;
  actor: string;
  verb: string;
  object: ActivityObject;
  sentimentScore: number;
  sentimentTimeStamp: number;
  domainName: string;
  dateTime: Date;
}
