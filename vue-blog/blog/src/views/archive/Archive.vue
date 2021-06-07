<template>
  <div>
    <!-- banner -->
    <div class="archive-banner banner">
      <h1 class="banner-title">归档</h1>
    </div>
    <!-- 归档列表 -->
    <v-card class="blog-container">
      <timeline>
        <timeline-title> 目前共计{{ count }}篇文章，继续加油 </timeline-title>
        <timeline-item v-for="item of archiveList" :key="item.id">
          <!-- 日期 -->
          <span class="time">{{ item.createTime | date }}</span>
          <!-- 文章标题 -->
          <router-link
            :to="'/articles/' + item.id"
            style="color:#666;text-decoration: none"
          >
            {{ item.title }}
          </router-link>
        </timeline-item>
      </timeline>
      <!-- 分页按钮 -->
      <v-pagination
        color="#00C4B6"
        v-model="current"
        :length="Math.ceil(count / 10)"
        total-visible="7"
      />
    </v-card>
  </div>
</template>

<script>
import { Timeline, TimelineItem, TimelineTitle } from "vue-cute-timeline";
export default {
  created() {
    this.listArchives();
  },
  components: {
    Timeline,
    TimelineItem,
    TimelineTitle
  },
  data: function() {
    return {
      current: 1,
      count: 0,
      archiveList: []
    };
  },
  methods: {
    listArchives() {
      this.axios
        .get("/api/articles/archives", {
          params: { current: this.current }
        })
        .then(({ data }) => {
          this.archiveList = data.data.records;
          this.count = data.data.total;
        });
    }
  },
  watch: {
    current(value) {
      this.axios
        .get("/api/articles/archives", {
          params: { current: value }
        })
        .then(({ data }) => {
          this.archiveList = data.data.records;
          this.count = data.data.total;
        });
    }
  }
};
</script>

<style scoped>
.archive-banner {
  background: url("http://qtysfkbdh.hn-bkt.clouddn.com/ab82a43e-0ffd-46b6-a53f-5e41653c329b")
    center center / cover no-repeat;
  background-color: #49b1f5;
}
.time {
  font-size: 0.75rem;
  color: #555;
  margin-right: 1rem;
}
</style>
