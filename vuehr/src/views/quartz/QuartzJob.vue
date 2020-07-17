<template>
    <div>

        <div style="display: flex;justify-content: space-between">
            <div>
                <el-input placeholder="请输入baen名称"
                          clearable
                          @clear="initJobs"
                          style="width: 200px;margin-right: 10px" v-model="searchValue.beanName"
                          @keydown.enter.native="initJobs" :disabled="showAdvanceSearchView"></el-input>
                <el-input placeholder="请输入方法名称"
                          clearable
                          @clear="initJobs"
                          style="width: 200px;margin-right: 10px" v-model="searchValue.methodName"
                          @keydown.enter.native="initJobs" :disabled="showAdvanceSearchView"></el-input>
                <el-select v-model="searchValue.status" filterable placeholder="请选择">
                    <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                    </el-option>
                </el-select>
                <el-button icon="el-icon-search" type="primary" style="margin-left: 20px" @click="initJobs"
                           :disabled="showAdvanceSearchView">
                    搜索
                </el-button>
                <!--                    <el-button type="primary" @click="showAdvanceSearchView = !showAdvanceSearchView">-->
                <!--                        <i :class="showAdvanceSearchView?'fa fa-angle-double-up':'fa fa-angle-double-down'"-->
                <!--                           aria-hidden="true"></i>-->
                <!--                        高级搜索-->
                <!--                    </el-button>-->
            </div>
            <div>

                <el-button type="success" @click="exportData(multipleSelection)" icon="el-icon-download">
                    导出数据
                </el-button>
                <el-button type="primary" icon="el-icon-plus" @click="showAddJobView">
                    添加任务
                </el-button>
            </div>
        </div>

        <div style="margin-top: 10px">
            <el-table
                    :data="Jobs"
                    stripe
                    border
                    v-loading="loading"
                    element-loading-text="正在加载..."
                    element-loading-spinner="el-icon-loading"
                    element-loading-background="rgba(0, 0, 0, 0.8)"
                    @selection-change="handleSelectionChange"
                    style="width: 100%">
                <el-table-column
                        type="selection"
                        width="75">
                </el-table-column>
                <el-table-column
                        type="index"
                        fixed
                        align="left"
                        label="序号"
                        width="50">
                </el-table-column>
                <el-table-column
                        prop="jobId"
                        fixed
                        align="left"
                        label="任务ID"
                        width="70">
                </el-table-column>
                <el-table-column
                        prop="beanName"
                        fixed
                        align="left"
                        label="bean名称"
                        width="150">
                </el-table-column>
                <el-table-column
                        prop="methodName"
                        fixed
                        align="left"
                        label="方法名称"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="params"
                        fixed
                        align="left"
                        label="参数"
                        width="150">
                </el-table-column>
                <el-table-column
                        prop="cronExpression"
                        fixed
                        align="left"
                        label="Cron表达式"
                        width="120">
                </el-table-column>
                <el-table-column
                        prop="remark"
                        fixed
                        align="left"
                        label="备注"
                        width="250">
                </el-table-column>
                <el-table-column
                        prop="createTime"
                        fixed
                        align="left"
                        label="创建时间"
                        width="180">
                    <template  scope="scope">
                             {{timestampToTime(scope.row.createTime)}}
                    </template>
                </el-table-column>

                <el-table-column
                        prop="status"
                        fixed
                        align="left"
                        label="状态"
                        width="100">
                    <template slot-scope="scope">
                        <el-button v-if="scope.row.status==1" style="padding: 3px" size="small" type="danger">暂停
                        </el-button>
                        <el-button v-if="scope.row.status==0" style="padding: 3px" size="small" type="green">正常
                        </el-button>
                    </template>
                </el-table-column>
                <el-table-column
                        fixed="right"
                        width="180"
                        label="操作">
                    <template slot-scope="scope">
                        <el-button @click="showEditJobView(scope.row)" style="padding: 3px" size="small">编辑</el-button>
                        <!--                            <el-button style="padding: 3px" size="mini" type="primary">查看高级资料</el-button>-->
                        <el-button @click="deleteJob(scope.row)" style="padding: 3px" size="small" type="danger">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div style="display: flex;justify-content: flex-end">
                <el-pagination
                        background
                        @current-change="currentChange"
                        @size-change="sizeChange"
                        layout="sizes, prev, pager, next, jumper, ->, total, slot"
                        :total="total">
                </el-pagination>
            </div>
        </div>
        <el-dialog
                :title="title"
                :visible.sync="dialogVisible"
                width="40%">
            <div>
                <el-form  :label-position="labelPosition" label-width="100px" :model="ruleFrom" :rules="rules" ref="jobFrom">
                    <el-form-item  label="bean名称:" prop="beanName" style="align-content: center">
                        <el-input size="mini" style="width: 400px" prefix-icon="el-icon-edit"
                                  v-model="ruleFrom.beanName"
                        ></el-input>
                    </el-form-item>
                    <el-form-item label="方法名称:" prop="methodName">
                        <el-input size="small" style="width: 400px" prefix-icon="el-icon-edit"
                                  v-model="ruleFrom.methodName"
                        ></el-input>
                    </el-form-item>
                    <el-form-item label="参数:" prop="preantName">
                        <el-input size="mini" style="width: 400px" prefix-icon="el-icon-edit" v-model="ruleFrom.params"
                        ></el-input>
                    </el-form-item>
                    <el-form-item label="cron表达式:" prop="preantName">
                        <el-input size="mini" style="width: 400px" prefix-icon="el-icon-edit"
                                  v-model="ruleFrom.cronExpression"
                        ></el-input>
                    </el-form-item>
<!--                    <el-form-item label="状态:" prop="preantName">-->
<!--                        <el-input size="mini" style="width: 400px" prefix-icon="el-icon-edit" v-model="ruleFrom.status"-->
<!--                        ></el-input>-->
<!--                    </el-form-item>-->
                    <el-form-item label="描述:" prop="preantName">
                        <el-input type="textarea"
                                  :rows="4"
                                  placeholder="请输入内容"    style="width: 400px" prefix-icon="el-icon-edit" v-model="ruleFrom.remark"
                        ></el-input>
                    </el-form-item>
<!--                    <el-form-item label="创建时间:" prop="preantName">-->
<!--                        <el-input size="mini" style="width: 150px" prefix-icon="el-icon-edit"-->
<!--                                  v-model="ruleFrom.createTime"-->
<!--                        ></el-input>-->
<!--                    </el-form-item>-->
                </el-form>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click=" submitForm('jobFrom')">确 定</el-button>
    <el-button @click="dialogVisible = false">取 消</el-button>

  </span>
        </el-dialog>
    </div>
</template>


<script>
    import {postRequest} from "../../utils/api";
    import {exportMethod} from '@/utils/util';
    import axios from 'axios'

    export default {
        name: "QuartzJob",
        data() {
            return {
                searchValue: {
                    beanName: null,
                    methodName: null,
                    status: null,
                    createTime: null,
                    createTimeFrom: null,
                    createTimeTo: null,
                    cronExpression: null,
                    jobId: null,
                    params: null,
                    remark: null
                },
                title: '添加任务',
                ids:[],
                labelPosition: 'right',
                importDataBtnText: '导入数据',
                importDataBtnIcon: 'el-icon-upload2',
                importDataDisabled: false,
                showAdvanceSearchView: false,
                allDeps: [],
                Jobs: [],
                loading: false,
                popVisible: false,
                popVisible2: false,
                dialogVisible: false,
                total: 0,
                page: 1,
                keyword: '',
                keyword1: '',
                size: 10,
                filterText: '',
                job: {
                    beanName: null,
                    methodName: null,
                    status: null,
                    createTime: null,
                    createTimeFrom: null,
                    createTimeTo: null,
                    cronExpression: null,
                    jobId: null,
                    params: null,
                    remark: null
                },
                rules: {},
                pname: '',
                deps: [],
                multipleSelection: [],

                inputDepName: '任务',

                defaultProps: {
                    children: 'children',
                    label: 'name'
                },
                ruleFrom: {
                    beanName: null,
                    methodName: null,
                    status: null,
                    createTime: null,
                    createTimeFrom: null,
                    createTimeTo: null,
                    cronExpression: null,
                    jobId: null,
                    params: null,
                    remark: null
                },
                options: [{
                    value: '0',
                    label: '正常'
                }, {
                    value: '1',
                    label: '暂停'
                }]

            }
        },
        mounted() {
            this.initJobs();
            // this.initData();
            // this.initPositions();
        },
        methods: {
            timestampToTime(timestamp) {
                var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
                var Y = date.getFullYear() + '-';
                var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
                var D = (date.getDate() < 10 ? '0'+date.getDate() : date.getDate()) + ' ';
                var h = (date.getHours() < 10 ? '0'+date.getHours() : date.getHours()) + ':';
                var m = (date.getMinutes() < 10 ? '0'+date.getMinutes() : date.getMinutes()) + ':';
                var s = (date.getSeconds() < 10 ? '0'+date.getSeconds() : date.getSeconds());
                return Y+M+D+h+m+s;
                // return Y+M+D;
            },

            handleSelectionChange(val) {
                console.log(val)
                this.multipleSelection = val;
            },
            searvhViewHandleNodeClick(data) {
                this.inputDepName = data.name;
                this.searchValue.departmentId = data.id;
                this.popVisible2 = !this.popVisible2
            },
            // onError(err, file, fileList) {
            //     this.importDataBtnText = '导入数据';
            //     this.importDataBtnIcon = 'el-icon-upload2';
            //     this.importDataDisabled = false;
            // },
            // onSuccess(response, file, fileList) {
            //     this.importDataBtnText = '导入数据';
            //     this.importDataBtnIcon = 'el-icon-upload2';
            //     this.importDataDisabled = false;
            //     this.initEmps();
            // },
            // beforeUpload() {
            //     this.importDataBtnText = '正在导入';
            //     this.importDataBtnIcon = 'el-icon-loading';
            //     this.importDataDisabled = true;
            // },
            exportData(rows) {
                var _this = this;
                rows.forEach(element  => {
                    _this.ids.push(element.jobId);
                })
                window.open('/job/exportExcel?ids=' + _this.ids , '_parent');
            },
            initDep() {
                this.dep = {
                    name: '',
                    parentId: -1
                }
                this.pname = '';
            },
            showAddJobView() {
                this.dialogVisible = true;
                // this.AddJob();
            },
            showEditJobView(data) {
                this.title = '编辑任务信息';
                this.ruleFrom = data;
                this.dialogVisible = true;
                this.submitForm(data);
            },
            submitForm(data) {
                if (this.ruleFrom.jobId) {
                    console.log(this.ruleFrom)
                    this.$refs['jobFrom'].validate(valid => {
                        if (valid) {
                            this.postRequest("/job/update", this.ruleFrom).then(resp => {
                                if (resp) {
                                    this.dialogVisible = false;
                                    this.initJobs();
                                }
                            })
                        }
                    });
                } else {
                    this.$refs['jobFrom'].validate(valid => {
                        if (valid) {
                            this.postRequest("/job/addJob", this.ruleFrom).then(resp => {
                                if (resp) {
                                    this.dialogVisible = false;
                                    this.initJobs();
                                }
                            })
                        }
                    });
                }
                // const _this = this;
                // this.$refs[ruleFrom].validate((valid) => {
                //     if (valid) {
                //         console.log(this.ruleFrom)
                //         // this.postRequest("/job/addJob",JSON.stringify(this.ruleFrom)).then(resp => {
                //             axios.post("/job/addJob",this.ruleFrom).then(resp => {
                //             if (resp) {
                //                 this.initJobs();
                //             } else {
                //                 this.$message.error("添加失败");
                //             }
                //         });
                //     } else {
                //         console.log('error submit!!');
                //         return false;
                //     }
                // });

            },
            deleteJob(data) {
                this.$confirm('此操作将永久删除【' + data.beanName + '】, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.getRequest("/job/delete/" + data.jobId).then(resp => {
                        if (resp) {
                            this.initJobs();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },



            // handleNodeClick(data) {
            //     this.inputDepName = data.name;
            //     this.emp.departmentId = data.id;
            //     this.popVisible = !this.popVisible
            // },
            // showDepView() {
            //     this.popVisible = !this.popVisible
            // },
            // showDepView2() {
            //     this.popVisible2 = !this.popVisible2
            // },
            // initPositions() {
            //     this.getRequest('/employee/basic/positions').then(resp => {
            //         if (resp) {
            //             this.positions = resp;
            //         }
            //     })
            // },
            // getMaxWordID() {
            //     this.getRequest("/employee/basic/maxWorkID").then(resp => {
            //         if (resp) {
            //             this.emp.workID = resp.obj;
            //         }
            //     })
            // },
            // initData() {
            //     if (!window.sessionStorage.getItem("nations")) {
            //         this.getRequest('/employee/basic/nations').then(resp => {
            //             if (resp) {
            //                 this.nations = resp;
            //                 window.sessionStorage.setItem("nations", JSON.stringify(resp));
            //             }
            //         })
            //     } else {
            //         this.nations = JSON.parse(window.sessionStorage.getItem("nations"));
            //     }
            //     if (!window.sessionStorage.getItem("joblevels")) {
            //         this.getRequest('/employee/basic/joblevels').then(resp => {
            //             if (resp) {
            //                 this.joblevels = resp;
            //                 window.sessionStorage.setItem("joblevels", JSON.stringify(resp));
            //             }
            //         })
            //     } else {
            //         this.joblevels = JSON.parse(window.sessionStorage.getItem("joblevels"));
            //     }
            //     if (!window.sessionStorage.getItem("politicsstatus")) {
            //         this.getRequest('/employee/basic/politicsstatus').then(resp => {
            //             if (resp) {
            //                 this.politicsstatus = resp;
            //                 window.sessionStorage.setItem("politicsstatus", JSON.stringify(resp));
            //             }
            //         })
            //     } else {
            //         this.politicsstatus = JSON.parse(window.sessionStorage.getItem("politicsstatus"));
            //     }
            //     if (!window.sessionStorage.getItem("deps")) {
            //         this.getRequest('/employee/basic/deps').then(resp => {
            //             if (resp) {
            //                 this.allDeps = resp;
            //                 window.sessionStorage.setItem("deps", JSON.stringify(resp));
            //             }
            //         })
            //     } else {
            //         this.allDeps = JSON.parse(window.sessionStorage.getItem("deps"));
            //     }
            // },
            sizeChange(currentSize) {
                this.size = currentSize;
                this.initJobs();
            },
            currentChange(currentPage) {
                this.page = currentPage;
                this.initJobs();
            },
            // showAddJobView() {
            //     this.emptyEmp();
            //     this.title = '添加部门';
            //     this.getMaxWordID();
            //     this.dialogVisible = true;
            // },
            initJobs(type) {
                this.loading = true;
                let url = 'job/jobList'
                let params = ''
                params = {
                    beanName: this.searchValue.beanName,
                    methodName: this.searchValue.methodName,
                    status: this.searchValue.status,
                    pageNum: this.page,
                    pageSize: this.size
                }
                this.getRequest(url, params).then(resp => {
                    this.loading = false;
                    if (resp) {
                        this.Jobs = resp.obj.records;
                        this.total = resp.obj.total;
                    }
                });
            },
            selectPreant(val) {
                this.loading = true;
                // let url = '/mgrsystem/basic/department/selectOne'+val ;

                // url += "&name=" + this.keyword;

                this.getRequest("/mgrsystem/basic/department/selectOne" + '?id=' + val).then(resp => {
                    this.loading = false;
                    if (resp) {
                        this.pname = resp.obj.name;

                    }
                });

            }
        }
    }
</script>

<style>
    /* 可以设置不同的进入和离开动画 */
    /* 设置持续时间和动画函数 */
    .slide-fade-enter-active {
        transition: all .8s ease;
    }

    .slide-fade-leave-active {
        transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
    }

    .slide-fade-enter, .slide-fade-leave-to
        /* .slide-fade-leave-active for below version 2.1.8 */
    {
        transform: translateX(10px);
        opacity: 0;
    }

</style>
