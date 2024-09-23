<template>

    <v-card outlined>
        <v-card-title>
            Mypage # {{$route.params.id }}
        </v-card-title>

        <v-card-text>
            <div>
                <Number label="UserId" v-model="item.userId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="BookId" v-model="item.bookId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="HotelId" v-model="item.hotelId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="HotelName" v-model="item.hotelName" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Date label="BookDt" v-model="item.bookDt" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="BookStaus" v-model="item.bookStaus" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="Userpoint" v-model="item.userpoint" :editMode="editMode" @change="change" />
            </div>
        </v-card-text>
    </v-card>

</template>

<script>
  const axios = require('axios').default;

  export default {
    name: 'MypageViewDetail',
    props: {
      value: Object,
    },
    data: () => ({
        item : [],
    }),
    async created() {
      var params = this.$route.params;
      var temp = await axios.get(axios.fixUrl('/mypages/' + params.id))

      this.item = temp.data;

    },
    methods: {
    }
  }
</script>

