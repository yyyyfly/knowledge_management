import request from './request'

export interface CheckinItem {
  id?: number
  title: string
  description?: string
  frequency: 'daily' | 'weekly' | 'monthly' | 'quarterly' | 'yearly'
  userId?: number
  status?: string
  recCreator?: number
  recCreateTime?: string
  recRevisor?: number
  recReviseTime?: string
  archFlag?: number
}

export interface CheckinRecord {
  id?: number
  checkinItemId: number
  userId?: number
  checkinTime?: string
  cycleKey?: string
  recCreator?: number
  recCreateTime?: string
  recRevisor?: number
  recReviseTime?: string
  archFlag?: number
}

// 打卡项目相关API
export const createCheckinItem = (item: CheckinItem) => {
  return request.post('/checkin/item', item)
}

export const getCheckinItemList = () => {
  return request.get('/checkin/item/list')
}

export const getCheckinItemListByStatus = (status: string) => {
  return request.get(`/checkin/item/list/${status}`)
}

export const getCheckinItemById = (id: number) => {
  return request.get(`/checkin/item/${id}`)
}

export const updateCheckinItem = (id: number, item: CheckinItem) => {
  return request.put(`/checkin/item/${id}`, item)
}

export const deleteCheckinItem = (id: number) => {
  return request.delete(`/checkin/item/${id}`)
}

// 打卡记录相关API
export const checkin = (itemId: number) => {
  return request.post(`/checkin/record/checkin/${itemId}`)
}

export const cancelCheckin = (itemId: number) => {
  return request.delete(`/checkin/record/cancel/${itemId}`)
}

export const checkCheckinStatus = (itemId: number, frequency: string) => {
  return request.get(`/checkin/record/status/${itemId}`, { params: { frequency } })
}

export const getCheckinRecords = (itemId: number) => {
  return request.get(`/checkin/record/list/${itemId}`)
}


