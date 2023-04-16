import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/api/user/login',
    method: 'post',
    params: {
      username: data.username,
      password: data.password
    }
  })
}

export function register(data) {
  return request({
    url: '/api/user/register',
    method: 'get',
    params: {
      username: data.username,
      password: data.password
    }
  })
}

export function getInfo(token) {
  return request({
    url: '/api/user/getUserInfo',
    method: 'get',
    params: {token}
  })
}

export function logout() {
  return request({
    url: '/api/user/logout',
    method: 'post'
  })
}
