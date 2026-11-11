export type validationMessage = {
  value: boolean,
  message: string,
}

export function validateUsername(username: string) : validationMessage {
  if (username.trim() == '') {
    return { value: false, message: 'Username required' };
  }

  if (username.length < 4) {
    return { value: false, message: 'Username lenght minimum is 4!' };
  }

  if (username.length > 20) {
    return { value: false, message: 'Username lenght maximum is 20!' };
  }

  return { value: true, message: '' };

}

export function validatePassword(password: string) : validationMessage {
  if (password.trim() == '') {
    return { value: false, message: 'Password required' };
  }

  if (password.length < 8) {
    return { value: false, message: 'Password lenght minimum is 8!' };
  }

  if (password.length > 64) {
    return { value: false, message: 'Password lenght maximum is 64!' };
  }

  return { value: true, message: '' };
}

