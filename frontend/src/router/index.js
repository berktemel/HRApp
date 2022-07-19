import {createRouter, createWebHistory} from 'vue-router'
import EmployeeList from '../components/EmployeeList.vue'
import EmployeeDetail from '../components/EmployeeDetail.vue'
import EditEmployee from '../views/EditEmployee.vue'
import DepartmentList from '../components/DepartmentList.vue'
import DepartmentDetail from '../components/DepartmentDetail.vue'
import ResetPassword from '../components/ResetPassword.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: EmployeeList
  },
  {
    path: '/departments',
    name: 'departments',
    component: DepartmentList
  },
  {
    path: '/addemployee',
    name: 'addemployee',
    component: function () {
      return import('../views/AddEmployee.vue')
    }
  },
  {
    path: '/employee/:id',
    name: 'employee',
    component: EmployeeDetail,
    props: true
  },
  {
    path: '/edit/:id',
    name: 'edit',
    component: EditEmployee,
    props: true
  },
  {
    path: '/department/:id',
    name: 'department',
    component: DepartmentDetail,
    props: true
  },
  {
    path: '/adddepartment',
    name: 'adddepartment',
    component: function () {
      return import('../views/AddDepartment.vue')
    }
  },
  {
    path: '/login',
    name: 'login',
    component: function () {
      return import('../components/LoginPage.vue')
    }
  },
  {
    path: '/signup',
    name: 'signup',
    component: function () {
      return import('../components/SignupPage.vue')
    }
  },
  {
    path: '/users',
    name: 'users',
    component: function () {
      return import('../components/UsersList.vue')
    }
  },
  {
    path: '/forgotpassword',
    name: 'forgotpassword',
    component: function () {
      return import('../components/ForgotPassword.vue')
    }
  },
  {
    path: '/resetpassword/:str',
    name: "resetpassword",
    component: ResetPassword,
    props: true
  },
  {
    path: '/users/pending',
    name: "userspending",
    component: function () {
      return import('../components/UsersPending.vue')
    }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
