export interface UserProfil {
  id: string;
  username: string;
  email: string;
  created_at: string;
  updated_at: string;
}

export interface UpdateUserProfil {
  user: UserProfil;
  accessToken: string;
}
