<tDeptlate>
    <div>
        <div>
            <div style="display: flex;justify-content: space-between">
                <div>
                    <el-input placeholder="请输入部门进行搜索，可以直接回车搜索..." prefix-icon="el-icon-search"
                              clearable
                              @clear="initDepts"
                              style="width: 350px;margin-right: 10px" v-model="keyword"
                              @keydown.enter.native="initDepts" :disabled="showAdvanceSearchView"></el-input>
                    <el-button icon="el-icon-search" type="primary" @click="initDepts" :disabled="showAdvanceSearchView">
                        搜索
                    </el-button>
                    <el-button type="primary" @click="showAdvanceSearchView = !showAdvanceSearchView">
                        <i :class="showAdvanceSearchView?'fa fa-angle-double-up':'fa fa-angle-double-down'"
                           aria-hidden="true"></i>
                        高级搜索
                    </el-button>
                </div>
                <div>
                    <el-upload
                            :show-file-list="false"
                            :before-upload="beforeUpload"
                            :on-success="onSuccess"
                            :on-error="onError"
                            :disabled="importDataDisabled"
                            style="display: inline-flex;margin-right: 8px"
                            action="/Deptloyee/basic/import">
                        <el-button :disabled="importDataDisabled" type="success" :icon="importDataBtnIcon">
                            {{importDataBtnText}}
                        </el-button>
                    </el-upload>
                    <el-button type="success" @click="exportData" icon="el-icon-download">
                        导出数据
                    </el-button>
                    <el-button type="primary" icon="el-icon-plus" @click="showAddDeptView">
                        添加用户
                    </el-button>
                </div>
            </div>
        </div>
        <div style="margin-top: 10px">
            <el-table
                    :data="Depts"
                    stripe
                    border
                    v-loading="loading"
                    element-loading-text="正在加载..."
                    element-loading-spinner="el-icon-loading"
                    element-loading-background="rgba(0, 0, 0, 0.8)"
                    style="width: 100%">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="name"
                        fixed
                        align="left"
                        label="部门名称"
                        width="90">
                </el-table-column>
                <el-table-column
                        prop="workID"
                        label="部门描述"
                        align="left"
                        width="185">
                </el-table-column>
                <el-table-column
                        prop="gender"
                        label="部门路径"
                        align="left"
                        width="185">
                </el-table-column>

                <el-table-column
                        fixed="right"
                        width="200"
                        label="操作">
                    <tDeptlate slot-scope="scope">
                        <el-button @click="showEditDeptView(scope.row)" style="padding: 5px" size="mini">编辑</el-button>
                        <el-button style="padding: 5px" size="mini" type="primary">查看高级资料</el-button>
                        <el-button @click="deleteDept(scope.row)" style="padding: 5px" size="mini" type="danger">删除
                        </el-button>
                    </tDeptlate>
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
                width="50%">
            <div>
                <el-form :model="Dept" :rules="rules" ref="DeptForm" label-width="500px">
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="部门名称:" prop="name">
                                <el-input size="mini" style="width: 150px" prefix-icon="el-icon-edit" v-model="Dept.name"
                                          placeholder="请输入员部门名称"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="成立日期:" prop="birthday">
                                <el-date-picker
                                        v-model="Dept.birthday"
                                        size="mini"
                                        type="date"
                                        value-format="yyyy-MM-dd"
                                        style="width: 150px;"
                                        placeholder="成立日期">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="7">
                            <el-form-item label="部门描述:" prop="politicId">
                                <el-select v-model="Dept.politicId" placeholder="部门描述" size="mini" style="width: 100%;">
                                    <el-option
                                            v-for="item in politicsstatus"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="上级部门:" prop="nationId">
                                <el-select v-model="Dept.nationId" placeholder="上级部门" size="mini" style="width: 100%;">
                                    <el-option
                                            v-for="item in nations"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
            </div>
            <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="doAddDept">确 定</el-button>
  </span>
        </el-dialog>
    </div>
</tDeptlate>

<script>
    export default {
        name: "DeptBasic",
        data() {
            return {
                searchValue: {
                    politicId: null,
                    nationId: null,
                    jobLevelId: null,
                    posId: null,
                    engageForm: null,
                    departmentId: null,
                    beginDateScope: null
                },
                title: '',
                importDataBtnText: '导入数据',
                importDataBtnIcon: 'el-icon-upload2',
                importDataDisabled: false,
                showAdvanceSearchView: false,
                allDeps: [],
                Depts: [],
                loading: false,
                popVisible: false,
                popVisible2: false,
                dialogVisible: false,
                total: 0,
                page: 1,
                keyword: '',
                size: 10,
                nations: [],
                joblevels: [],
                politicsstatus: [],
                name: "财务部",
                depPath: ".1.4.5.8",
                parentId: "-1",
                enabled: "1",
                isParent: "1",

                defaultProps: {
                    children: 'children',
                    label: 'name'
                },
                rules: {
                    name: [{required: true, message: '请输入用户名', trigger: 'blur'}],
                }
            }
        },
        mounted() {
            this.initDepts();
            this.initData();
            // this.initPositions();
        },
        methods: {
            searvhViewHandleNodeClick(data) {
                this.inputDepName = data.name;
                this.searchValue.departmentId = data.id;
                this.popVisible2 = !this.popVisible2
            },
            onError(err, file, fileList) {
                this.importDataBtnText = '导入数据';
                this.importDataBtnIcon = 'el-icon-upload2';
                this.importDataDisabled = false;
            },
            onSuccess(response, file, fileList) {
                this.importDataBtnText = '导入数据';
                this.importDataBtnIcon = 'el-icon-upload2';
                this.importDataDisabled = false;
                this.initDepts();
            },
            beforeUpload() {
                this.importDataBtnText = '正在导入';
                this.importDataBtnIcon = 'el-icon-loading';
                this.importDataDisabled = true;
            },
            exportData() {
                window.open('/Deptloyee/basic/export', '_parent');
            },
            DepttyDept() {
                this.Dept = {
                    id:"",
                    name: "",
                    gender: "",
                    birthday: "",
                    idCard: "",
                    wedlock: "",
                    nationId: 1,
                    nativePlace: "",
                    politicId: 13,
                    email: "",
                    phone: "",
                    address: "",
                    departmentId: null,
                    jobLevelId: 9,
                    posId: 29,
                    engageForm: "",
                    tiptopDegree: "",
                    specialty: "",
                    school: "",
                    beginDate: "",
                    workID: "",
                    contractTerm: 2,
                    conversionTime: "",
                    notworkDate: null,
                    beginContract: "",
                    endContract: "",
                    workAge: null
                }
                this.inputDepName = '';
            },
            showEditDeptView(data) {
                this.initPositions();
                this.title = '编辑员工信息';
                this.Dept = data;
                this.inputDepName = data.department.name;
                this.dialogVisible = true;
            },
            deleteDept(data) {
                this.$confirm('此操作将永久删除【' + data.name + '】, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/mgrsystem/basic/department/" + data.id).then(resp => {
                        if (resp) {
                            this.initDepts();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            doAddDept() {
                if (this.Dept.id) {
                    this.$refs['DeptForm'].validate(valid => {
                        if (valid) {
                            this.putRequest("/mgrsystem/basic/department/", this.Dept).then(resp => {
                                if (resp) {
                                    this.dialogVisible = false;
                                    this.initDepts();
                                }
                            })
                        }
                    });
                } else {
                    this.$refs['DeptForm'].validate(valid => {
                        if (valid) {
                            this.postRequest("/mgrsystem/basic/department/", this.Dept).then(resp => {
                                if (resp) {
                                    this.dialogVisible = false;
                                    this.initDepts();
                                }
                            })
                        }
                    });
                }
            },
            handleNodeClick(data) {
                this.inputDepName = data.name;
                this.Dept.departmentId = data.id;
                this.popVisible = !this.popVisible
            },
            showDepView() {
                this.popVisible = !this.popVisible
            },
            showDepView2() {
                this.popVisible2 = !this.popVisible2
            },
            // initPositions() {
            //     this.getRequest('/Deptloyee/basic/positions').then(resp => {
            //         if (resp) {
            //             this.positions = resp;
            //         }
            //     })
            // },
            getMaxWordID() {
                this.getRequest("/Deptloyee/basic/maxWorkID").then(resp => {
                    if (resp) {
                        this.Dept.workID = resp.obj;
                    }
                })
            },
            // initData() {
            //     if (!window.sessionStorage.getItem("nations")) {
            //         this.getRequest('/Deptloyee/basic/nations').then(resp => {
            //             if (resp) {
            //                 this.nations = resp;
            //                 window.sessionStorage.setItem("nations", JSON.stringify(resp));
            //             }
            //         })
            //     } else {
            //         this.nations = JSON.parse(window.sessionStorage.getItem("nations"));
            //     }
            //     if (!window.sessionStorage.getItem("joblevels")) {
            //         this.getRequest('/Deptloyee/basic/joblevels').then(resp => {
            //             if (resp) {
            //                 this.joblevels = resp;
            //                 window.sessionStorage.setItem("joblevels", JSON.stringify(resp));
            //             }
            //         })
            //     } else {
            //         this.joblevels = JSON.parse(window.sessionStorage.getItem("joblevels"));
            //     }
            //     if (!window.sessionStorage.getItem("politicsstatus")) {
            //         this.getRequest('/Deptloyee/basic/politicsstatus').then(resp => {
            //             if (resp) {
            //                 this.politicsstatus = resp;
            //                 window.sessionStorage.setItem("politicsstatus", JSON.stringify(resp));
            //             }
            //         })
            //     } else {
            //         this.politicsstatus = JSON.parse(window.sessionStorage.getItem("politicsstatus"));
            //     }
            //     if (!window.sessionStorage.getItem("deps")) {
            //         this.getRequest('/Deptloyee/basic/deps').then(resp => {
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
                this.initDepts();
            },
            currentChange(currentPage) {
                this.page = currentPage;
                this.initDepts('advanced');
            },
            showAddDeptView() {
                this.DepttyDept();
                this.title = '添加部门';
                this.getMaxWordID();
                this.dialogVisible = true;
            },
            initDepts(type) {
                this.loading = true;
                let url = '/mgrsystem/basic/department/pageList?page=' + this.page + '&size=' + this.size;
                url += "&name=" + this.keyword;
                this.getRequest(url).then(resp => {
                    this.loading = false;
                    if (resp) {
                        this.Depts = resp.data;
                        this.total = resp.total;
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
