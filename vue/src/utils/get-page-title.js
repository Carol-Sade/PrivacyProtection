import defaultSettings from '@/settings'

const title = defaultSettings.title || '隐私保护数据共享系统'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
