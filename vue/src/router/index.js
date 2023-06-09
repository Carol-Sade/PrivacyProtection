import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'
import path from "path";

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/register',
    component: () => import('@/views/register/index'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: {title: '首页', icon: 'el-icon-s-home'}
    }]
  },

  {
    path: '/privacy',
    component: Layout,
    //meta: {title: '用户', icon: 'user'},
    children: [
      {
        path: 'index',
        component: () => import('@/views/privacy/index.vue'),
        meta: {title: '隐私保护', icon: 'el-icon-unlock'}
      }
    ]
  },

  {
    path: '/share',
    component: Layout,
    //meta: {title: '用户', icon: 'user'},
    children: [
      {
        path: 'index',
        component: () => import('@/views/share/index.vue'),
        meta: {title: '数据共享', icon: 'el-icon-share'}
      }
    ]
  },

  // {
  //   path: '/example',
  //   component: Layout,
  //   redirect: '/example/table',
  //   name: 'Example',
  //   meta: {title: 'Example', icon: 'el-icon-s-help'},
  //   children: [
  //     {
  //       path: 'table',
  //       name: 'Table',
  //       component: () => import('@/views/table/index'),
  //       meta: {title: 'Table', icon: 'table'}
  //     },
  //     {
  //       path: 'tree',
  //       name: 'Tree',
  //       component: () => import('@/views/tree/index'),
  //       meta: {title: 'Tree', icon: 'tree'}
  //     }
  //   ]
  // },
  //
  // {
  //   path: '/form',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'index',
  //       name: 'Form',
  //       component: () => import('@/views/form/index'),
  //       meta: {title: 'Form', icon: 'form'}
  //     }
  //   ]
  // },
  //
  // {
  //   path: '/nested',
  //   component: Layout,
  //   redirect: '/nested/menu1',
  //   name: 'Nested',
  //   meta: {
  //     title: 'Nested',
  //     icon: 'nested'
  //   },
  //   children: [
  //     {
  //       path: 'menu1',
  //       component: () => import('@/views/nested/menu1/index'), // Parent router-view
  //       name: 'Menu1',
  //       meta: {title: 'Menu1'},
  //       children: [
  //         {
  //           path: 'menu1-1',
  //           component: () => import('@/views/nested/menu1/menu1-1'),
  //           name: 'Menu1-1',
  //           meta: {title: 'Menu1-1'}
  //         },
  //         {
  //           path: 'menu1-2',
  //           component: () => import('@/views/nested/menu1/menu1-2'),
  //           name: 'Menu1-2',
  //           meta: {title: 'Menu1-2'},
  //           children: [
  //             {
  //               path: 'menu1-2-1',
  //               component: () => import('@/views/nested/menu1/menu1-2/menu1-2-1'),
  //               name: 'Menu1-2-1',
  //               meta: {title: 'Menu1-2-1'}
  //             },
  //             {
  //               path: 'menu1-2-2',
  //               component: () => import('@/views/nested/menu1/menu1-2/menu1-2-2'),
  //               name: 'Menu1-2-2',
  //               meta: {title: 'Menu1-2-2'}
  //             }
  //           ]
  //         },
  //         {
  //           path: 'menu1-3',
  //           component: () => import('@/views/nested/menu1/menu1-3'),
  //           name: 'Menu1-3',
  //           meta: {title: 'Menu1-3'}
  //         }
  //       ]
  //     },
  //     {
  //       path: 'menu2',
  //       component: () => import('@/views/nested/menu2/index'),
  //       name: 'Menu2',
  //       meta: {title: 'menu2'}
  //     }
  //   ]
  // },

]

export const asyncRoutes = [
  {
    path: '/blockChain',
    redirect: '/dashboard',
    component: Layout,
    meta: {title: '区块链管理', icon: 'blockChain', roles: ['2']},
    children: [
      {
        path: 'upload',
        component: () => import('@/views/blockChain/upload/index.vue'),
        meta: {title: '智能合约操作', icon: 'el-icon-upload2', roles: ['2']}
      },
      {
        path: 'option',
        component: () => import('@/views/blockChain/uploadUbuntu/index.vue'),
        meta: {title: '区块链服务器', icon: 'el-icon-s-operation', roles: ['2']},
      }
    ]
  },
  {
    path: '/examine',
    component: Layout,
    meta: {title: '共享文件审核', icon: 'el-icon-s-check', roles: ['2']},
    children: [
      {
        path: 'examineFile',
        component: () => import('@/views/examine/examineFile/index.vue'),
        meta: {title: '文件审核', icon: 'el-icon-edit', roles: ['2']}
      },
      {
        path: 'option',
        component: () => import('@/views/examine/option/index.vue'),
        meta: {title: '操作记录', icon: 'el-icon-paperclip', roles: ['2']},
      }
    ]
  },
  {
    path: '/user',
    component: Layout,
    meta: {title: '用户', icon: 'el-icon-user'},
    children: [
      {
        path: 'information',
        component: () => import('@/views/user/information/information.vue'),
        meta: {title: '用户信息', icon: 'el-icon-tickets'}
      },
      {
        path: 'option',
        component: () => import('@/views/user/option/option.vue'),
        meta: {title: '操作记录', icon: 'el-icon-paperclip'}
      },
      {
        path: 'collect',
        component: () => import('@/views/user//collect/index.vue'),
        meta: {title: '我的收藏', icon: 'el-icon-star-off'}
      },
    ]
  },
  {
    path: '/feedback',
    component: Layout,
    meta: {title: '反馈', icon: 'el-icon-user'},
    children: [
      {
        path: 'userFeedback',
        component: () => import('@/views/feedback/user/index.vue'),
        meta: {title: '反馈', icon: 'el-icon-tickets'}
      },
      {
        path: 'adminFeedback',
        component: () => import('@/views/feedback/admin/index.vue'),
        meta: {title: '查看反馈', icon: 'el-icon-paperclip', roles: ['2']}
      }
    ]
  },
  // 404 page must be placed at the end !!!
  {path: '*', redirect: '/404', hidden: true}
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({y: 0}),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
