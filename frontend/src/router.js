
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);


import BookingBookingManager from "./components/listers/BookingBookingCards"
import BookingBookingDetail from "./components/listers/BookingBookingDetail"

import PointPointManager from "./components/listers/PointPointCards"
import PointPointDetail from "./components/listers/PointPointDetail"

import HotelHotelManager from "./components/listers/HotelHotelCards"
import HotelHotelDetail from "./components/listers/HotelHotelDetail"



export default new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: [
            {
                path: '/bookings/bookings',
                name: 'BookingBookingManager',
                component: BookingBookingManager
            },
            {
                path: '/bookings/bookings/:id',
                name: 'BookingBookingDetail',
                component: BookingBookingDetail
            },

            {
                path: '/points/points',
                name: 'PointPointManager',
                component: PointPointManager
            },
            {
                path: '/points/points/:id',
                name: 'PointPointDetail',
                component: PointPointDetail
            },

            {
                path: '/hotels/hotels',
                name: 'HotelHotelManager',
                component: HotelHotelManager
            },
            {
                path: '/hotels/hotels/:id',
                name: 'HotelHotelDetail',
                component: HotelHotelDetail
            },




    ]
})
