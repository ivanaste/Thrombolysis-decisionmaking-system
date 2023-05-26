import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Store } from '@ngrx/store';
import {
  changeUserRole,
  createUser,
  deleteUser,
} from '../../../store/users.actions';
import { ActivatedRoute } from '@angular/router';
import { selectUserById } from '../../../store/users.selectors';
import { Subscription } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from '../../../../shared/components/confirmation-dialog/confirmation-dialog.component';
import { FormMode } from '../../../../shared/model/form-mode';
import { User } from '../../../../shared/model/user.model';

@Component({
  selector: 'app-user-profile-form',
  templateUrl: './user-profile-form.component.html',
  styleUrls: ['./user-profile-form.component.scss'],
})
export class UserProfileFormComponent implements OnInit, OnDestroy {
  userProfileForm!: FormGroup;
  mode: FormMode = FormMode.NEW;
  storeSubscription!: Subscription;
  userId!: string;
  oldUser!: User;
  userChanged = false;

  constructor(
    private store: Store,
    private route: ActivatedRoute,
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    this.userId = this.route.snapshot.params['id'];
    let email = '';
    let role = '';
    if (this.userId) {
      this.mode = FormMode.EDIT;
      this.storeSubscription = this.store
        .select(selectUserById(this.userId))
        .subscribe((user) => {
          this.oldUser = user;
          email = user.email;
          role = user.role;
        });
    } else {
      this.mode = FormMode.NEW;
      role = 'TENANT';
    }
    this.userProfileForm = new FormGroup({
      email: new FormControl(email, [Validators.required, Validators.email]),
      role: new FormControl(role, [Validators.required]),
    });
    if (this.mode === FormMode.EDIT) {
      this.userProfileForm.controls['email'].disable();
      this.userProfileForm.controls['role'].valueChanges.subscribe((value) => {
        this.userChanged = value !== this.oldUser.role;
      });
    }
  }

  ngOnDestroy() {
    this.storeSubscription?.unsubscribe();
  }

  submitUserProfileForm() {
    if (this.userProfileForm.valid) {
      const email = this.userProfileForm.controls['email'].value;
      const role = this.userProfileForm.controls['role'].value;
      if (this.mode === FormMode.NEW) {
        const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
          data: {
            title: 'User Creation',
            text: `Are you sure you want to create a new user?`,
          },
        });

        dialogRef.afterClosed().subscribe((result) => {
          if (result) {
            this.store.dispatch(createUser({ email, role }));
          }
        });
      } else if (this.oldUser.role !== role) {
        const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
          data: {
            title: 'User Edit',
            text: `Are you sure you want to edit the user?`,
          },
        });

        dialogRef.afterClosed().subscribe((result) => {
          if (result) {
            this.store.dispatch(changeUserRole({ id: this.userId, role }));
          }
        });
      }
    }
  }

  deleteUser($event: MouseEvent) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: {
        title: 'User Deletion',
        text: `Are you sure you want to delete the user?`,
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.store.dispatch(deleteUser({ id: this.userId }));
      }
    });

    $event.preventDefault();
  }

  protected readonly FormMode = FormMode;
}
